import { IGenerationProcess } from '@/shared/model/generation-process.model';
import { IAkipEntity } from '@/shared/model/akip-entity.model';

export class TaskGenerateDomainEntityContext {
  taskInstance?: any = {};
  generationProcess?: IGenerationProcess = {};
  akipEntityDomain?: IAkipEntity = {};
}
