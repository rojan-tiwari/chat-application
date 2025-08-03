import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ChatService } from '../../services/chat.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css'],
})
export class ChatComponent {
  messages: string[] = [];
  messageInput = '';

  constructor(private chatService: ChatService) {
  }

  
  ngOnInit(): void {
  if (typeof window !== 'undefined') {
    this.chatService.connect('ws://localhost:8080/chat');
    this.chatService.onMessage().subscribe((msg) => {
      this.messages.push(msg);
    });
  }
}


  sendMessage(): void {
    if (this.messageInput.trim()) {
      this.chatService.sendMessage(this.messageInput);
      this.messageInput = '';
    }
  }
}
