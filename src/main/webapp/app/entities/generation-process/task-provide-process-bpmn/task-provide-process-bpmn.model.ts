import { IGenerationProcess } from '@/shared/model/generation-process.model';

export class TaskProvideProcessBpmnContext {
  taskInstance?: any = {};
  generationProcess?: IGenerationProcess = {};
}
