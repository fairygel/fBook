#!/bin/bash

# help menu
show_help() {
    echo "usage: ./start.sh [options]"
    echo
    echo "when no options are provided, script will try to start 'fBook.jar'"
    echo "if 'fBook.jar' is not exist, we will build project using './gradlew'"
    echo
    echo "options:"
    echo "  -f, --force-build     force the build of the project."
    echo "  -r, --docker-run      run the application using Docker."
    echo "  -d, --database        start the database using Docker before running the application."
    echo "  -h, --help            show this help message."
    echo
}

# building project using gradle
build_project() {
    echo "building project..."

    if [[ -f "./gradlew" ]]; then
        ./gradlew clean build -x test

        if [[ -f "build/libs/fBook-0.0.1-SNAPSHOT.jar" ]]; then
            mv build/libs/fBook-0.0.1-SNAPSHOT.jar fBook.jar
            echo "built successfully!"
        else
            echo "there was some errors when building. please, check build logs to fix them."
            exit 1
        fi
    else
        echo "file 'gradlew' not found. try to restore it using 'git restore .' or clone project again."
        exit 1
    fi
}

# check if docker is installed
check_docker() {
    if ! command -v docker &> /dev/null; then
        echo "please, install Docker first to run this feature."
        exit 1
    fi
}

parse_application_yml() {
    local key=$1
    grep -oP "(?<=${key}: ).*" src/main/resources/application.yml | tr -d '\r'
}

# running database using docker
run_database() {
    echo "starting database using Docker..."

    local db_user=$(parse_application_yml "username")
    local db_password=$(parse_application_yml "password")
    local db_name=$(parse_application_yml "url" | grep -oP "(?<=/)[^?]+")

    if [[ $(sudo docker ps -a -q -f name=fbook-db) ]]; then
        sudo docker start fbook-db || {
            echo "failed to start existing database container. check your Docker setup."
            exit 1
        }
    else
        sudo docker run -d --name fbook-db \
            -e POSTGRES_USER="$db_user" \
            -e POSTGRES_PASSWORD="$db_password" \
            -e POSTGRES_DB="$db_name" \
            -p 5432:5432 \
            postgres:14.15-alpine || {
            echo "failed to start new database container. check your Docker setup."
            exit 1
        }
    fi
    echo "database started successfully."
}


# stops database container
stop_database() {
    echo "stopping database..."
    sudo docker stop fbook-db || {
        echo "failed to stop database. check your Docker setup."
    }
    echo "database stopped."
}

# composing app with docker
run_with_docker() {
    echo "running application using Docker..."

    if [[ $force_build == true ]]; then
        echo "force-building Docker image..."
        sudo docker build -t fbook-backend . || {
            echo "failed to build Docker image. check your Dockerfile."
            exit 1
        }
    fi

    sudo docker compose up || {
        echo "failed to start application with Docker. check your Docker setup."
        exit 1
    }
    echo "application started successfully with Docker."
}

check_java_version() {
# check if java installed
    if ! command -v java &> /dev/null; then
        echo "please, install java 21 first to run fBook api."
        exit 1
    fi

    # getting java version
    java_version=$(java -version 2>&1 | head -n 1 | grep -oP '(?<=version ")[^"]+')

    # java version should be equal to 21
    if [[ $java_version != 21* ]]; then
        echo "installed java version(java: $java_version) is not equal to 21. please, reinstall it."
        exit 1
    fi
}

force_build=false
docker_run=false
database=false

# parse arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        --force-build|-f)
            force_build=true
            shift
            ;;
        --docker-run|-r)
            docker_run=true
            shift
            ;;
        --database|-d)
            database=true
            shift
            ;;
        --help|-h)
            show_help
            exit 0
            ;;
        *)
            echo "Unknown option: $1"
            show_help
            exit 1
            ;;
    esac
done

if [[ $docker_run == true ]]; then
    check_docker
    run_with_docker
else
    check_java_version

    if [[ $database == true ]]; then
        check_docker
        run_database
    fi

    # building project, if fBook.jar doesn't exist
    if [[ $force_build == true || ! -f "fBook.jar" ]]; then
        build_project
    fi

    echo "starting application..."
    java -jar fBook.jar

    if [[ $database == true ]]; then
          stop_database
    fi
fi
