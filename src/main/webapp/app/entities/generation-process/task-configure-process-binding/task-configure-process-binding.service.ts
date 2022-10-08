import axios from 'axios';
import { TaskConfigureProcessBindingContext } from './task-configure-process-binding.model';

const baseApiUrl = 'api/generation-process/task-configure-process-binding';

export default class TaskConfigureProcessBindingService {
  public loadContext(taskId: number): Promise<TaskConfigureProcessBindingContext> {
    return new Promise<TaskConfigureProcessBindingContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskConfigureProcessBindingContext> {
    return new Promise<TaskConfigureProcessBindingContext>((resolve, reject) => {
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

  public complete(taskConfigureProcessBindingContext: TaskConfigureProcessBindingContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskConfigureProcessBindingContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
