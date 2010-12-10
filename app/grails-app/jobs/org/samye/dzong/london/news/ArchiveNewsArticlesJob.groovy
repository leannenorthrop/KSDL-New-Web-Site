package org.samye.dzong.london.news


class ArchiveNewsArticlesJob {
    def sessionRequired = true
    def articleService

    static triggers = {
        cron name: 'monthly', cronExpression: "0 0 02 L * ?"
    }

    def group = "LSD"

    def execute() {
        articleServer.archiveNewsArticles()
    }    
}
