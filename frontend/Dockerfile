FROM node:18.19-alpine AS build

WORKDIR /app/

COPY package.json ./
COPY package-lock.json ./

RUN npm install

COPY . .

RUN npm run build -- --configuration production

FROM nginx:1.22.1-alpine

COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

COPY docker.nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]