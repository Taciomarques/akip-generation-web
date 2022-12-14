import { IAkipProcess } from '@/shared/model/akip-process.model';

export interface IGenerationProcess {
  id?: number;
  processInstance?: any | null;
  akipProcess?: IAkipProcess | null;
  data?: any | null;
}

export class GenerationProcess implements IGenerationProcess {
  constructor(
    public id?: number,
    public processInstance?: any | null,
    public akipProcess?: IAkipProcess | null,
    public data?: any | null
  ) {}
}
