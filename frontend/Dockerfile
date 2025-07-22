# Stage 1: Build the Angular app
FROM node:18 AS builder

WORKDIR /app
COPY . .

RUN npm install
RUN npm run build -- --configuration production

# Stage 2: Serve the app with nginx
FROM nginx:alpine

COPY --from=builder /app/dist/frontend /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
