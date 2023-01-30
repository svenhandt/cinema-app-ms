import {PresentationModel} from "./presentation.model";

export class FilmWeekDayModel {

  private _id: string | undefined;
  private _weekdayName: string | undefined;
  private _presentations: PresentationModel[] | undefined;

  constructor(id: string | undefined, weekday: string | undefined, presentations: PresentationModel[] | undefined) {
    this._id = id;
    this._weekdayName = weekday;
    this._presentations = presentations;
  }

  get id(): string | undefined {
    return this._id;
  }

  get weekdayName(): string | undefined {
    return this._weekdayName;
  }

  get presentations(): PresentationModel[] | undefined {
    return this._presentations;
  }
}
