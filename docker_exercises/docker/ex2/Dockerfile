FROM node:lts-alpine3.23

WORKDIR /user/src/app

COPY ./sources/package.json ./

RUN npm install

COPY ./sources/index.js ./

EXPOSE 8080

CMD ["node", "index.js"]