import { IAkipEntity } from '@/shared/model/akip-entity.model';
import { IAkipProcess } from '@/shared/model/akip-process.model';

export interface IAkipApplication {
  id?: number;
  name?: string | null;
  repositoryName?: string | null;
  createDate?: Date | null;
  properties?: string | null;
  etities?: IAkipEntity[] | null;
  processes?: IAkipProcess[] | null;
}

export class AkipApplication implements IAkipApplication {
  constructor(
    public id?: number,
    public name?: string | null,
    public repositoryName?: string | null,
    public createDate?: Date | null,
    public properties?: string | null,
    public etities?: IAkipEntity[] | null,
    public processes?: IAkipProcess[] | null
  ) {}
}
