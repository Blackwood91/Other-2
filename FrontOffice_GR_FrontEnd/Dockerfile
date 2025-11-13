### STAGE 1: Build ###
FROM node:18-alpine AS build
WORKDIR /usr/src/app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build
### STAGE 2: Run ###
FROM nginx:1.17.1-alpine
ENV TZ=Europe/Rome
COPY --from=build /usr/src/app/dist/giustizia-riparativa-FO /usr/share/nginx/html