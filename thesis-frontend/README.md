# ThesisFrontend

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.2.6. It is currently using [Angular](https://angular.io/docs)
version 15.0.0.

## Installation

### node.js

To be able to run the project locally, you will need to have node.js installed with the version, that is supporting currently used Angular (check [compatibility](https://stackoverflow.com/questions/60248452/is-there-a-compatibility-list-for-angular-angular-cli-and-node-js)). The CI/CD flow is currently defined with the version 16 and that's the recommended one. You can download node.js for your operating system from [here](https://nodejs.org/en/download/).

### Angular CLI

After installing node.js you will need to install Angular CLI. It's a tool designed for the work with Angular apps. Run

```
npm install -g @angular/cli
```

in your command line/terminal, to install it globally.

## Run the project

In order to run the project, the steps are as follows:

1. run `npm install` inside of the `thesis-frontend` folder
2. run `npm run start` to start the local dev server, by default it will run on `http://localhost:4200/` and it will reload after every change you make
3. run `npm run test` to execute unit tests via [Karma](https://karma-runner.github.io)

If you need to run the server with mocked data, you can use `npm run mock:server` command.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.
