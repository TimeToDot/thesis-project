import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { ButtonComponent } from '../button/button.component';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { FileInfo } from '../../models/file-info.model';

@Component({
  selector: 'bvr-file-upload',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './file-upload.component.html',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: FileUploadComponent,
    },
  ],
})
export class FileUploadComponent implements ControlValueAccessor {
  file: FileInfo = { name: '', src: '' };
  touched: boolean = false;

  constructor(private sanitizer: DomSanitizer) {}

  onFileSelected(event: any) {
    this.markAsTouched();
    const file: File = event.target.files[0];
    if (file) {
      this.file.name = file.name;
      this.file.src = this.sanitizer.bypassSecurityTrustUrl(
        URL.createObjectURL(file)
      );
      this.onChange(this.file);
    }
  }

  writeValue(file: FileInfo | null): void {
    this.file = file ? file : { name: '', src: '' };
  }

  onChange = (file: FileInfo) => {};

  registerOnChange(onChange: any) {
    this.onChange = onChange;
  }

  onTouched = () => {};

  registerOnTouched(onTouched: any) {
    this.onTouched = onTouched;
  }

  markAsTouched() {
    if (!this.touched) {
      this.onTouched();
      this.touched = true;
    }
  }
}
