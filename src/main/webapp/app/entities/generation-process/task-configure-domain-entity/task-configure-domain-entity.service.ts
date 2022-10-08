import axios from 'axios';
import { TaskConfigureDomainEntityContext } from './task-configure-domain-entity.model';

const baseApiUrl = 'api/generation-process/task-configure-domain-entity';

export default class TaskConfigureDomainEntityService {
  public loadContext(taskId: number): Promise<TaskConfigureDomainEntityContext> {
    return new Promise<TaskConfigureDomainEntityContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskConfigureDomainEntityContext> {
    return new Promise<TaskConfigureDomainEntityContext>((resolve, reject) => {
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

  public complete(taskConfigureDomainEntityContext: TaskConfigureDomainEntityContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskConfigureDomainEntityContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
