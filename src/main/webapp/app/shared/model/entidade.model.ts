import { IProcess } from '@/shared/model/process.model';
import { IApplication } from '@/shared/model/application.model';

import { TypeEntity } from '@/shared/model/enumerations/type-entity.model';
export interface IEntidade {
  id?: number;
  name?: string | null;
  fields?: string | null;
  relations?: string | null;
  type?: TypeEntity | null;
  process?: IProcess | null;
  application?: IApplication | null;
}

export class Entidade implements IEntidade {
  constructor(
    public id?: number,
    public name?: string | null,
    public fields?: string | null,
    public relations?: string | null,
    public type?: TypeEntity | null,
    public process?: IProcess | null,
    public application?: IApplication | null
  ) {}
}
