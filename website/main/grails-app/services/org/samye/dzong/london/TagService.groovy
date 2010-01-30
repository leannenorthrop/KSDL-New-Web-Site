package org.samye.dzong.london

class TagService {
    def sessionFactory
    boolean transactional = true

    def tagCounts() {
        def session = sessionFactory.getCurrentSession()
        def query = session.createQuery("select T.name,count(*) from Tag T, TagLink TL, Publishable P where (T.id = TL.tag.id) and (TL.tagRef = P.id) and (P.publishState = 'Published' or P.publishState = 'Archived') group by T.id, T.name order by T.name")
        return query.list()
    }
}
