import axios from 'axios';
import { TaskGenerateUserTaskProcessContext } from './task-generate-user-task-process.model';

const baseApiUrl = 'api/generation-process/task-generate-user-task-process';

export default class TaskGenerateUserTaskProcessService {
  public loadContext(taskId: number): Promise<TaskGenerateUserTaskProcessContext> {
    return new Promise<TaskGenerateUserTaskProcessContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskGenerateUserTaskProcessContext> {
    return new Promise<TaskGenerateUserTaskProcessContext>((resolve, reject) => {
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

  public complete(taskGenerateUserTaskProcessContext: TaskGenerateUserTaskProcessContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskGenerateUserTaskProcessContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
