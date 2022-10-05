import { IGenerationProcess } from '@/shared/model/generation-process.model';
import { IAkipEntity } from '@/shared/model/akip-entity.model';

export class TaskGenerateStartFormProcessContext {
  taskInstance?: any = {};
  generationProcess?: IGenerationProcess = {};
  akipEntityStartForm?: IAkipEntity = {};
}
