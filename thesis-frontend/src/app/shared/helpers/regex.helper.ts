export class Regex {
  static readonly ALPHA: RegExp = new RegExp(/^[\p{L}\p{M}\s-]+$/u);
  static readonly ALPHANUMERIC: RegExp = new RegExp(/^[\p{L}\p{M}\p{N}\s-]+$/u);
  static readonly EMAIL: RegExp = new RegExp(
    /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  );
  static readonly NIP: RegExp = new RegExp(/^[\p{N}]{10}$/u);
  static readonly NUMERIC: RegExp = new RegExp(/^[\p{N}]+$/u);
  static readonly PASSWORD: RegExp = new RegExp(
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#$@!%&*?])[A-Za-z\d#$@!%&*?]{8,}$/
  );
  static readonly PESEL: RegExp = new RegExp(/^[\p{N}]{11}$/u);
  static readonly PHONE: RegExp = new RegExp(/^[+]?[\p{N}\s-]+$/u);
  static readonly REGON: RegExp = new RegExp(/^(?=[\p{N}]*$)(?:.{9}|.{14})$/u);
  static readonly WEBSITE: RegExp = new RegExp(
    /^[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&\//=]*)$/
  );
}
