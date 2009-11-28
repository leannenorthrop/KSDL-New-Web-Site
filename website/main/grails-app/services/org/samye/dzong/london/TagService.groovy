package org.samye.dzong.london

class TagService {
	def sessionFactory
    boolean transactional = true

    def tagCounts() {
        def session = sessionFactory.getCurrentSession()
 		def query = session.createQuery("select T.name,count(*) from Tag T, TagLink TL where (T.id = TL.tag.id) group by T.id, T.name order by T.name")
 		return query.list()
    }
}
