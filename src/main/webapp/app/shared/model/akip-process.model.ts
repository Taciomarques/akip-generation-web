import { IAkipEntity } from '@/shared/model/akip-entity.model';
import { IAkipApplication } from '@/shared/model/akip-application.model';
import { StatusProcess } from '@/shared/model/enumerations/status-process.model';
import { IAttachment } from '@/shared/model/attachment.model';

export interface IAkipProcess {
  id?: number;
  name?: string | null;
  percentageExecuted?: number | null;
  status?: StatusProcess | null;
  entities?: IAkipEntity[] | null;
  application?: IAkipApplication | null;
  attachments?: IAttachment[] | null;
  processBpmnId?: string | null;
}

export class AkipProcess implements IAkipProcess {
  constructor(
    public id?: number,
    public name?: string | null,
    public percentageExecuted?: number | null,
    public status?: StatusProcess | null,
    public entities?: IAkipEntity[] | null,
    public application?: IAkipApplication | null,
    public attachments?: IAttachment[] | null,
    public processBpmnId?: string | null
  ) {}
}
