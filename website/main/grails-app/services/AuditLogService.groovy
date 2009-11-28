class AuditLogService {

    boolean transactional = true

	def publishedOn(id) {
		def criteria = AuditLogEvent.createCriteria()
        def publish = criteria.list(){
            and {
                eq('eventName', 'UPDATE')
                eq('propertyName', 'publishState')
				eq('className', 'org.samye.dzong.london.community.Article')
				eq('newValue', 'Published')
				eq('persistedObjectId', id)
            }
        }
		return publish[0]
	}
	
	def createdOn(id) {
		def criteria = AuditLogEvent.createCriteria()
        def publish = criteria.list(){
            and {
                eq('eventName', 'INSERT')
				eq('className', 'org.samye.dzong.london.community.Article')
				eq('persistedObjectId', id)
            }
        }
		return publish[0]
	}
	
	def lastUpdatedOn(id) {
		def criteria = AuditLogEvent.createCriteria()
        def publish = criteria.list(){
            and {
                eq('eventName', 'UPDATE')
				not {
                	eq('propertyName', 'publishState')
				}
				eq('className', 'org.samye.dzong.london.community.Article')
				eq('persistedObjectId', id)
            }
        }
		return publish[0]
	}		
}
