import { FilmWeekDayModel } from "./film-week-day.model";

export class FilmModel {

  private _id: number | undefined;
  private _name: string | undefined;
  private _filmWeekDays: FilmWeekDayModel[] | undefined;


  get id(): number | undefined {
    return this._id;
  }

  set id(value: number | undefined) {
    this._id = value;
  }

  get name(): string | undefined {
    return this._name;
  }

  set name(value: string | undefined) {
    this._name = value;
  }


  get filmWeekDays(): FilmWeekDayModel[] | undefined {
    return this._filmWeekDays;
  }

  set filmWeekDays(value: FilmWeekDayModel[] | undefined) {
    this._filmWeekDays = value;
  }
}
