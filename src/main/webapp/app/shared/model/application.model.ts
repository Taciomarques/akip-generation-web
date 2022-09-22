import { IEntidade } from '@/shared/model/entidade.model';
import { IProcess } from '@/shared/model/process.model';

export interface IApplication {
  id?: number;
  name?: string | null;
  repositoryName?: string | null;
  createDate?: Date | null;
  properties?: string | null;
  etities?: IEntidade[] | null;
  processes?: IProcess[] | null;
}

export class Application implements IApplication {
  constructor(
    public id?: number,
    public name?: string | null,
    public repositoryName?: string | null,
    public createDate?: Date | null,
    public properties?: string | null,
    public etities?: IEntidade[] | null,
    public processes?: IProcess[] | null
  ) {}
}
