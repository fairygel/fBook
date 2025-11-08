#!/bin/bash

# help menu
show_help() {
    echo "usage: ./start.sh [options]"
    echo
    echo "options:"
    echo "  -f, --force-build   force rebuild all Docker images before starting."
    echo "  -h, --help          show this help message."
    echo
}

# is docker installed
check_docker() {
    if ! command -v docker &> /dev/null; then
        echo "please install docker first."
        exit 1
    fi
}

# run docker
run_docker_compose() {
    if [[ "$force_build" == true ]]; then
        echo "forcing rebuild of all docker images..."
        sudo docker compose up --build
    else
        echo "starting docker containers..."
        sudo docker compose up
    fi
}

force_build=false

while [[ $# -gt 0 ]]; do
    case $1 in
        -f|--force-build)
            force_build=true
            shift
            ;;
        -h|--help)
            show_help
            exit 0
            ;;
        *)
            echo "unknown option: $1"
            show_help
            exit 1
            ;;
    esac
done

check_docker
run_docker_compose