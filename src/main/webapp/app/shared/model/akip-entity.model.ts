import { IAkipProcess } from '@/shared/model/akip-process.model';
import { IAkipApplication } from '@/shared/model/akip-application.model';

import { TypeEntity } from '@/shared/model/enumerations/type-entity.model';
import { IAkipField } from '@/shared/model/akip-field.model';
import { IAkipRelationship } from '@/shared/model/akip-relationship.model';
export interface IAkipEntity {
  id?: number;
  name?: string | null;
  fields?: IAkipField[] | null;
  relationships?: IAkipRelationship[] | null;
  type?: TypeEntity | null;
  processes?: IAkipProcess[] | null;
  application?: IAkipApplication | null;
  readOnly?: boolean | null;
  generated?: boolean | null;
}

export class AkipEntity implements IAkipEntity {
  constructor(
    public id?: number,
    public name?: string | null,
    public fields?: IAkipField[] | null,
    public relationships?: IAkipRelationship[] | null,
    public type?: TypeEntity | null,
    public processes?: IAkipProcess[] | null,
    public application?: IAkipApplication | null,
    public readOnly?: boolean | null,
    public generated?: boolean | null
  ) {
    this.fields ? this.fields : (this.fields = []);
    this.relationships ? this.relationships : (this.relationships = []);
  }
}
