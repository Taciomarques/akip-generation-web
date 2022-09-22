import axios from 'axios';
import { TaskProvideProcessBpmnContext } from './task-provide-process-bpmn.model';

const baseApiUrl = 'api/generation-process/task-provide-process-bpmn';

export default class TaskProvideProcessBpmnService {
  public loadContext(taskId: number): Promise<TaskProvideProcessBpmnContext> {
    return new Promise<TaskProvideProcessBpmnContext>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${taskId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public claim(taskId: number): Promise<TaskProvideProcessBpmnContext> {
    return new Promise<TaskProvideProcessBpmnContext>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${taskId}/claim`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public complete(taskProvideProcessBpmnContext: TaskProvideProcessBpmnContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskProvideProcessBpmnContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
