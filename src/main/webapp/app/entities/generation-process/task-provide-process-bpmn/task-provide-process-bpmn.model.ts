import { IGenerationProcess } from '@/shared/model/generation-process.model';
import { IAttachment } from '../../../shared/model/attachment.model';

export class TaskProvideProcessBpmnContext {
  taskInstance?: any = {};
  generationProcess?: IGenerationProcess = {};
  bpmn?: IAttachment = {};
}
