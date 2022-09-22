import { IProcess } from '@/shared/model/process.model';

export interface IGenerationProcess {
  id?: number;
  processInstance?: any | null;
  process?: IProcess | null;
}

export class GenerationProcess implements IGenerationProcess {
  constructor(public id?: number, public processInstance?: any | null, public process?: IProcess | null) {}
}
