import { IAkipProcess } from '@/shared/model/akip-process.model';
import { IAkipApplication } from '@/shared/model/akip-application.model';

import { TypeEntity } from '@/shared/model/enumerations/type-entity.model';
export interface IAkipEntity {
  id?: number;
  name?: string | null;
  fields?: string | null;
  relations?: string | null;
  type?: TypeEntity | null;
  process?: IAkipProcess | null;
  application?: IAkipApplication | null;
}

export class AkipEntity implements IAkipEntity {
  constructor(
    public id?: number,
    public name?: string | null,
    public fields?: string | null,
    public relations?: string | null,
    public type?: TypeEntity | null,
    public process?: IAkipProcess | null,
    public application?: IAkipApplication | null
  ) {}
}
