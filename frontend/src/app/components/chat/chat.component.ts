import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ChatService } from '../../services/chat.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css'],
})
export class ChatComponent implements OnInit {

  messageInput = '';
  sidebarOpen = false;
  typingUsers: string[] = [];

  onlineUsers: {
    username: string;
    avatar?: string;
    status?: string;
    statusText?: string;
  }[] = [];

  messages: {
    text: string;
    username: string;
    timestamp: Date;
    isOwn: boolean;
    status?: 'sent' | 'delivered';
    avatar?: string;
  }[] = [];

  currentUser = {
    username: '',
    avatar: 'src/assets/default-avatar.png', // Default avatar path
  };
  constructor(private chatService: ChatService, private authService: AuthService) {}

  ngOnInit(): void {
    this.currentUser.username = this.authService.getCurrentUser() || 'Anonymous';

    if (typeof window !== 'undefined') {
      this.chatService.connect(`ws://localhost:8080/chat?username=${this.currentUser.username}`);

      // subscribe to server broadcast
      this.chatService.onMessage().subscribe((data) => {
        try {
          const msgObj = JSON.parse(data);
          if (msgObj.type === 'user_list') {
            this.onlineUsers = msgObj.users;
          } else {
            // optional dedup: if last message has same id/content/timestamp skip
            this.messages.push({
              text: msgObj.content,
              username: msgObj.sender,
              timestamp: new Date(msgObj.timestamp || Date.now()),
              isOwn: msgObj.sender === this.currentUser.username,
              status: 'delivered',
              avatar: msgObj.avatar || 'default-avatar.png'
            });
          }
        } catch (e) {
          console.error('Invalid message from server', e, data);
        }
      });
    }
  }

  sendMessage(): void {
    if (!this.messageInput.trim()) return;

    const messageObject = {
      sender: this.currentUser.username,
      content: this.messageInput
    };

    // do NOT append locally - wait for server broadcast (prevents duplicates)
    this.chatService.sendMessage(JSON.stringify(messageObject));
    this.messageInput = '';
  }

  getMessageClass(msg: any): string { 
    return msg.isOwn ? 'own-message' : 'other-message';
  }

  getAvatarAltText(username: string): string {
    return `${username}'s avatar`;
  }

  getDefaultAvatar(): string {
    return 'default-avatar.png';
  }

  toggleSidebar(): void {
    this.sidebarOpen = !this.sidebarOpen;
  }

  onMessageSubmit(event: Event): void {
    event.preventDefault();
    this.sendMessage();
  }

  onTyping(): void {
    // Future: emit typing indicator
  }

  onInputFocus(): void {
    // Placeholder if needed
  }

  onInputBlur(): void {
    // Placeholder if needed
  }

  getTypingText(): string {
    if (this.typingUsers.length === 1) {
      return `${this.typingUsers[0]} is typing...`;
    }
    return `${this.typingUsers.join(', ')} are typing...`;
  }
}
