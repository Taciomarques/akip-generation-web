import { IGenerationProcess } from '@/shared/model/generation-process.model';
import { IAttachment } from '@/shared/model/attachment.model';

export class TaskGenerateEntitiesContext {
  taskInstance?: any = {};
  generationProcess?: IGenerationProcess = {};
  metadatasAkipEntitiesDomain?: IAttachment[] = [];
  metadataAkipEntityProcessBinding?: IAttachment = {};
  metadataAkipEntityStartForm?: IAttachment = {};
  metadatasAkipEntitiesUserTasks?: IAttachment[] = [];
  metadatasAkipEntitiesServiceTasks?: IAttachment[] = [];
}
