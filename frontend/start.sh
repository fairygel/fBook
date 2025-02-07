#!/bin/bash

# help menu
show_help() {
    echo "usage: ./start.sh [options]"
    echo
    echo "runs fbook web client, using npm"
    echo
    echo "options:"
    echo "  -h, --help            show this help message."
    echo "  -x, --nginx-deploy    deploy fbook web client to nginx"
    echo
}

# building client using npm
build_project() {
    echo "building client..."

    npm run clean
    npm run build -- --configuration production

    if [[ -d "dist/frontend/browser" ]]; then
        echo "built successfully!"
    else
        echo "there was some errors when building. please, check build logs to fix them."
        exit 1
    fi
}

# check if nginx is installed
check_nginx() {
    if ! command -v nginx &> /dev/null; then
        echo "please, install nginx, to deploy web client."
        exit 1
    fi

    if [[ ! -f "nginx.conf" ]]; then
        echo "there is no 'nginx.conf' file. try to restore it using 'git restore .' or clone project again."
    fi
}

deploy() {
    sudo cp -r dist/frontend/browser/* /usr/share/nginx/html
    sudo cp nginx.conf /etc/nginx/conf.d/default.conf

    sudo nginx -s quit
    sudo nginx -g "daemon off;"
}

# check if nodejs is installed
if ! command -v node &> /dev/null; then
    echo "please, install nodejs version 18 or higher to run fbook web client."
    exit 1
fi

# check if npm is installed
if ! command -v npm &> /dev/null; then
    echo "please install npm version 9 or higher to run fbook web client."
    exit 1
fi

nginx_deploy=false

while [[ $# -gt 0 ]]; do
    case $1 in
        --nginx-deploy|-x)
            nginx_deploy=true
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

if [[ $nginx_deploy == true ]]; then
    check_nginx
    build_project
    deploy
else
    echo "starting client..."
    npm start
fi
