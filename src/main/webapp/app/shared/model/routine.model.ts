export interface IRoutine {
  id?: string;
  register?: string;
  type?: string;
  desc?: string | null;
}

export class Routine implements IRoutine {
  constructor(public id?: string, public register?: string, public type?: string, public desc?: string | null) {}
}
