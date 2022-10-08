import axios from 'axios';
import { TaskConfigureStartFormProcessContext } from './task-configure-start-form-process.model';

const baseApiUrl = 'api/generation-process/task-configure-start-form-process';

export default class TaskConfigureStartFormProcessService {
  public loadContext(taskId: number): Promise<TaskConfigureStartFormProcessContext> {
    return new Promise<TaskConfigureStartFormProcessContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskConfigureStartFormProcessContext> {
    return new Promise<TaskConfigureStartFormProcessContext>((resolve, reject) => {
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

  public complete(taskConfigureStartFormProcessContext: TaskConfigureStartFormProcessContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskConfigureStartFormProcessContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
