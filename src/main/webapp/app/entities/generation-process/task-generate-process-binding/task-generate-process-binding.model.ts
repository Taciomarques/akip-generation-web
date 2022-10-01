import { IGenerationProcess } from '@/shared/model/generation-process.model';
import { IAkipEntity } from '@/shared/model/akip-entity.model';

export class TaskGenerateProcessBindingContext {
  taskInstance?: any = {};
  generationProcess?: IGenerationProcess = {};
  entity?: IAkipEntity = {};
  entities?: IAkipEntity[] = [];
}
