import { type IFingerprintTemplate } from '@/shared/model/fingerprint-template.model';

import { type FingerName } from '@/shared/model/enumerations/finger-name.model';
import { type HandName } from '@/shared/model/enumerations/hand-name.model';
export interface IFingerprint {
  id?: number;
  uuid?: string;
  fingerName?: keyof typeof FingerName;
  handName?: keyof typeof HandName;
  template?: IFingerprintTemplate | null;
}

export class Fingerprint implements IFingerprint {
  constructor(
    public id?: number,
    public uuid?: string,
    public fingerName?: keyof typeof FingerName,
    public handName?: keyof typeof HandName,
    public template?: IFingerprintTemplate | null,
  ) {}
}
