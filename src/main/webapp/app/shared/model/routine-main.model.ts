export interface IRoutineMain {
  id?: string;
  registerid?: string;
  description?: string | null;
}

export class RoutineMain implements IRoutineMain {
  constructor(public id?: string, public registerid?: string, public description?: string | null) {}
}
