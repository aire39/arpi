import socket

c_ip = input('Enter ip: ')

HOST, PORT = c_ip, 8888

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect((HOST,PORT))

user = input('Enter text: ')

client_socket.send(user.encode('utf-8'))
data = client_socket.recv(1024)
print (data)
client_socket.close()