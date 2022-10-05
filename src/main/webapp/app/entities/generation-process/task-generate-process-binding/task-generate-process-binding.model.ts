import { IGenerationProcess } from '@/shared/model/generation-process.model';
import { IAkipEntity } from '@/shared/model/akip-entity.model';

export class TaskGenerateProcessBindingContext {
  taskInstance?: any = {};
  generationProcess?: IGenerationProcess = {};
  akipEntityProcessBinding?: IAkipEntity = {};
  akipEntitiesDomain?: IAkipEntity[] = [];
}
