import axios from 'axios';
import { TaskConfigureServiceTaskProcessContext } from './task-configure-service-task-process.model';

const baseApiUrl = 'api/generation-process/task-configure-service-task-process';

export default class TaskConfigureServiceTaskProcessService {
  public loadContext(taskId: number): Promise<TaskConfigureServiceTaskProcessContext> {
    return new Promise<TaskConfigureServiceTaskProcessContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskConfigureServiceTaskProcessContext> {
    return new Promise<TaskConfigureServiceTaskProcessContext>((resolve, reject) => {
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

  public complete(taskConfigureServiceTaskProcessContext: TaskConfigureServiceTaskProcessContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskConfigureServiceTaskProcessContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
