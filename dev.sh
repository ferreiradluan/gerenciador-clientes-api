#!/bin/bash

# Script para facilitar o desenvolvimento local

set -e

echo "🚀 Gerenciador de Clientes - Ambiente de Desenvolvimento"
echo "========================================================="

# Função para mostrar ajuda
show_help() {
    echo "Comandos disponíveis:"
    echo "  ./dev.sh start    - Inicia o ambiente completo (banco + API)"
    echo "  ./dev.sh db       - Inicia apenas o banco PostgreSQL"
    echo "  ./dev.sh api      - Inicia apenas a API (requer banco rodando)"
    echo "  ./dev.sh stop     - Para todos os serviços"
    echo "  ./dev.sh clean    - Para e remove todos os containers"
    echo "  ./dev.sh logs     - Mostra logs dos serviços"
    echo "  ./dev.sh test     - Executa os testes"
    echo "  ./dev.sh help     - Mostra esta ajuda"
}

# Função para verificar se o Docker está instalado
check_docker() {
    if ! command -v docker &> /dev/null; then
        echo "❌ Docker não está instalado!"
        exit 1
    fi
    
    if ! command -v docker-compose &> /dev/null; then
        echo "❌ Docker Compose não está instalado!"
        exit 1
    fi
}

# Navegar para o diretório backend
cd backend

case "$1" in
    "start")
        echo "🔄 Iniciando ambiente completo..."
        check_docker
        docker-compose up -d
        echo "✅ Ambiente iniciado!"
        echo "📍 API: http://localhost:8080"
        echo "📍 PostgreSQL: localhost:5432"
        ;;
    
    "db")
        echo "🔄 Iniciando apenas o banco PostgreSQL..."
        check_docker
        docker-compose up -d postgres
        echo "✅ PostgreSQL iniciado!"
        echo "📍 PostgreSQL: localhost:5432"
        ;;
    
    "api")
        echo "🔄 Iniciando API Spring Boot..."
        ./mvnw spring-boot:run
        ;;
    
    "stop")
        echo "🛑 Parando serviços..."
        docker-compose stop
        echo "✅ Serviços parados!"
        ;;
    
    "clean")
        echo "🧹 Parando e removendo containers..."
        docker-compose down -v
        echo "✅ Containers removidos!"
        ;;
    
    "logs")
        echo "📋 Mostrando logs..."
        docker-compose logs -f
        ;;
    
    "test")
        echo "🧪 Executando testes..."
        ./mvnw test
        ;;
    
    "help" | "")
        show_help
        ;;
    
    *)
        echo "❌ Comando não reconhecido: $1"
        show_help
        exit 1
        ;;
esac
