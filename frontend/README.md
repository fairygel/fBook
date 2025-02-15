# fBook web client

## Dependencies
to build manually or using the script, you need to install the [Node.js](https://nodejs.org/en/download).
also, you must run [backend](../backend). you can run server using [nginx](http://nginx.org/en/download.html), so, you can download it too.
<br/>
if you don't care about all of them, you can just install [docker](https://docs.docker.com/engine/install/), and run the project without dependencies.
<br/>
to clone a project, you need to install [git](https://git-scm.com/downloads).

## Auto building and running using script
!! before start, you must run [backend](../backend)
if you are using linux, you can use `start.sh` to build and run the web client.
by default, it will run `ng serve`, that starts angular client.
you can use different flags, to get the result you need.
flags:
* -x, --nginx-deploy deploys build artifact to nginx.
* -h, --help only show the help menu.

## Manual building
!! before start, you must run [backend](../backend)
at first, clone repository:
```bash
git clone https://github.com/fairygel/fBook.git
cd fBook/frontend
```
then, we can set up some things, like proxy for rest api.
open `proxy.conf.json`. you will see something like this:
```json
{
  "/api": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true
  }
}
```
where `target` - is your api location. replace it with your own(or do nothing, if server is running on your pc).
may be you are so lazy, to do something else, so, you can just run
```shell
ng serve
```
and it will run server.
there is a chance(less than 1%), that you need to run client on nginx. so, we need to change `nginx.conf`:
```nginx configuration
server {
    listen 80;
    server_name localhost;

    root /usr/share/nginx/html;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    error_page 500 502 503 504 /50x.html;

    location = /50x.html {
        root /usr/share/nginx/html;
    }

    location = /favicon.ico {
        log_not_found off;
    }

    location /api/ {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

}
```
`listen 80` - is port, that nginx will use, replace it with needed.
`proxy_pass http://localhost:8080` - backend server. replace it or not, choose by yourself.
after setting up, run
```shell
nginx -g "daemon off;"
```
!! before run, be sure, that nginx is stopped !!