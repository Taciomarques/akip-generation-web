import axios from 'axios';
import { TaskGenerateProcessBindingContext } from './task-generate-process-binding.model';

const baseApiUrl = 'api/generation-process/task-generate-process-binding';

export default class TaskGenerateProcessBindingService {
  public loadContext(taskId: number): Promise<TaskGenerateProcessBindingContext> {
    return new Promise<TaskGenerateProcessBindingContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskGenerateProcessBindingContext> {
    return new Promise<TaskGenerateProcessBindingContext>((resolve, reject) => {
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

  public complete(taskGenerateProcessBindingContext: TaskGenerateProcessBindingContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskGenerateProcessBindingContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
