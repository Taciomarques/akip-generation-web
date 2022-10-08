import axios from 'axios';
import { TaskConfigureUserTaskProcessContext } from './task-configure-user-task-process.model';

const baseApiUrl = 'api/generation-process/task-configure-user-task-process';

export default class TaskConfigureUserTaskProcessService {
  public loadContext(taskId: number): Promise<TaskConfigureUserTaskProcessContext> {
    return new Promise<TaskConfigureUserTaskProcessContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskConfigureUserTaskProcessContext> {
    return new Promise<TaskConfigureUserTaskProcessContext>((resolve, reject) => {
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

  public complete(taskConfigureUserTaskProcessContext: TaskConfigureUserTaskProcessContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskConfigureUserTaskProcessContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
