export class Regex {
  static readonly ALPHA: RegExp = new RegExp(/^[\p{L}\p{M}\s-]+$/u);
  static readonly ALPHANUMERIC: RegExp = new RegExp(/^[\p{L}\p{M}\p{N}\s-]+$/u);
  static readonly EMAIL: RegExp = new RegExp(
    /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  );
  static readonly NUMERIC: RegExp = new RegExp(/^[\p{N}]+$/u);
  static readonly PESEL: RegExp = new RegExp(/^[\p{N}]{11}$/u);
  static readonly PHONE: RegExp = new RegExp(/^[+]?[\p{N}\s-]+$/u);
}
