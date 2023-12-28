import { type IFingerprint } from '@/shared/model/fingerprint.model';

export interface IFingerprintTemplate {
  id?: number;
  templateVersion?: string | null;
  templateDataContentType?: string | null;
  templateData?: string | null;
  originalImageContentType?: string | null;
  originalImage?: string | null;
  originalImageMime?: string | null;
  originalImageExtension?: string | null;
  fingerprint?: IFingerprint | null;
}

export class FingerprintTemplate implements IFingerprintTemplate {
  constructor(
    public id?: number,
    public templateVersion?: string | null,
    public templateDataContentType?: string | null,
    public templateData?: string | null,
    public originalImageContentType?: string | null,
    public originalImage?: string | null,
    public originalImageMime?: string | null,
    public originalImageExtension?: string | null,
    public fingerprint?: IFingerprint | null,
  ) {}
}
