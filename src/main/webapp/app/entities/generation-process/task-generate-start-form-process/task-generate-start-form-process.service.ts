import axios from 'axios';
import { TaskGenerateStartFormProcessContext } from './task-generate-start-form-process.model';

const baseApiUrl = 'api/generation-process/task-generate-start-form-process';

export default class TaskGenerateStartFormProcessService {
  public loadContext(taskId: number): Promise<TaskGenerateStartFormProcessContext> {
    return new Promise<TaskGenerateStartFormProcessContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskGenerateStartFormProcessContext> {
    return new Promise<TaskGenerateStartFormProcessContext>((resolve, reject) => {
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

  public complete(taskGenerateStartFormProcessContext: TaskGenerateStartFormProcessContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskGenerateStartFormProcessContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
