FROM node:latest as build
WORKDIR /app
COPY webapp/package*.json ./
RUN npm install
COPY webapp/. .
RUN npm run build

FROM nginx:latest
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
