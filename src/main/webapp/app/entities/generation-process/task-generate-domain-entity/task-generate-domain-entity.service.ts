import axios from 'axios';
import { TaskGenerateDomainEntityContext } from './task-generate-domain-entity.model';

const baseApiUrl = 'api/generation-process/task-generate-domain-entity';

export default class TaskGenerateDomainEntityService {
  public loadContext(taskId: number): Promise<TaskGenerateDomainEntityContext> {
    return new Promise<TaskGenerateDomainEntityContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskGenerateDomainEntityContext> {
    return new Promise<TaskGenerateDomainEntityContext>((resolve, reject) => {
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

  public complete(taskGenerateDomainEntityContext: TaskGenerateDomainEntityContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskGenerateDomainEntityContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
