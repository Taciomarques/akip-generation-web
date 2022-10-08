import { IGenerationProcess } from '@/shared/model/generation-process.model';
import { IAkipEntity } from '@/shared/model/akip-entity.model';

export class TaskConfigureServiceTaskProcessContext {
  taskInstance?: any = {};
  generationProcess?: IGenerationProcess = {};
  akipEntityServiceTask?: IAkipEntity = {};
}
