import axios from 'axios';
import { TaskGenerateEntitiesContext } from './task-generate-entities.model';

const baseApiUrl = 'api/generation-process/task-generate-entities';

export default class TaskGenerateEntitiesService {
  public loadContext(taskId: number): Promise<TaskGenerateEntitiesContext> {
    return new Promise<TaskGenerateEntitiesContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskGenerateEntitiesContext> {
    return new Promise<TaskGenerateEntitiesContext>((resolve, reject) => {
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

  public complete(taskGenerateEntitiesContext: TaskGenerateEntitiesContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskGenerateEntitiesContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
