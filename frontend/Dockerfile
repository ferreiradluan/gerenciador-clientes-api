# ARQUIVO: frontend/Dockerfile

# Usa a imagem oficial do Node como base (versão compatível com Angular 20)
FROM node:20.19-alpine
WORKDIR /app

# Copia os arquivos de definição de dependências
COPY package*.json ./

# Instala as dependências de forma otimizada
RUN npm ci

# Copia o restante dos arquivos do projeto
COPY . .

# Expõe a porta padrão do servidor de desenvolvimento do Angular
EXPOSE 4200

# Comando para iniciar o servidor, acessível de fora do container
CMD ["npm", "start", "--", "--host", "0.0.0.0"]
