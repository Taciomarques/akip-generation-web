import { IGenerationProcess } from '@/shared/model/generation-process.model';
import { IAkipEntity } from '@/shared/model/akip-entity.model';

export class TaskGenerateUserTaskProcessContext {
  taskInstance?: any = {};
  generationProcess?: IGenerationProcess = {};
  akipEntityUserTask?: IAkipEntity = {};
}
