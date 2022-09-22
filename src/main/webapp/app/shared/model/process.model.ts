import { IEntidade } from '@/shared/model/entidade.model';
import { IApplication } from '@/shared/model/application.model';

import { StatusProcess } from '@/shared/model/enumerations/status-process.model';
export interface IProcess {
  id?: number;
  name?: string | null;
  percentageExecuted?: number | null;
  status?: StatusProcess | null;
  etities?: IEntidade[] | null;
  application?: IApplication | null;
}

export class Process implements IProcess {
  constructor(
    public id?: number,
    public name?: string | null,
    public percentageExecuted?: number | null,
    public status?: StatusProcess | null,
    public etities?: IEntidade[] | null,
    public application?: IApplication | null
  ) {}
}
